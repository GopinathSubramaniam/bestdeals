package com.deals.service.payment;

import com.deals.model.Plan;
import com.deals.model.User;
import com.deals.service.PlanService;
import com.deals.service.UserService;
import com.deals.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by gsitgithub on 20/11/2016.
 */
@Service
public class PaymentServiceImpl implements PaymentService{

    @Value(value = "payuMoneyMode")
    private String payuMoneyMode;
    @Value(value = "payuMoneyLiveUrl")
    private String payuMoneyLiveUrl;
    @Value(value = "payuMoneyLiveKey")
    private String payuMoneyLiveKey;
    @Value(value = "payuMoneyLiveSalt")
    private String payuMoneyLiveSalt;

    @Value(value = "payuMoneyTestUrl")
    private String payuMoneyTestUrl;
    @Value(value = "payuMoneyTestKey")
    private String payuMoneyTestKey;
    @Value(value = "payuMoneyTestSalt")
    private String payuMoneyTestSalt;

    @Value(value = "successUrl")
    private String successUrl;
    @Value(value = "failureUrl")
    private String failureUrl;

    @Autowired UserService userService;
    @Autowired PlanService planService;
    private final String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";

    @Override
    public Map<String, Object> initPayment(Long byUserId, Long toUserId, Long planId, PaymentType type, double amount) {
        User user = userService.findOne(byUserId);
        String phone = user.getMobile();

        /*String base_url = "";
        String merchant_key = "";
        String salt = "";
        if( payuMoneyMode == "live"){
            base_url = payuMoneyLiveUrl;
            merchant_key = payuMoneyLiveKey;
            salt = payuMoneyLiveSalt;
        } else {
            base_url = payuMoneyTestUrl;
            merchant_key = payuMoneyTestKey;
            salt = payuMoneyTestSalt;
        }
        model.addAttribute("merchant_key",merchant_key);
*/
        String action1 ="";
        int error=0;
        String hashString="";
        String txnid ="";

        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
        txnid = hashCal("SHA-256",rndm).substring(0,20);

//        StringBuffer response_url = request.getRequestURL();// + "/payment_resp.jsp";
        Map<String, Object> params = new HashMap<>();
        /*params.put("amount",amount);
        params.put("txnid",txnid);
        params.put("udf2",udf2);
        params.put("serviceProvider","payu_paisa");
        params.put("planId",planId);
        params.put("plan",planName);
        params.put("phone",phone);
        params.put("udf1", planId);
        params.put("udf3", planName);
        params.put("surl", successUrl);
        params.put("furl", failureUrl);
        params.put("udf4", userId);*/

        PaymentVO pVo = new PaymentVO();
//        pVo.setId();
        pVo.setPhone(phone);
        pVo.setServiceProvider("payu_paisa");
        pVo.setSurl(successUrl);
        pVo.setFurl(failureUrl);
        pVo.setTxnid(txnid);

        if (type == PaymentType.BUY_PLAN && planId > 0) {
            Plan plan = (Plan) planService.findOne(planId).getData();
            //user.setPlan(plan);
            Double payAmount = plan.getAmount();
            String planName = plan.getName();

            pVo.setAmount(payAmount);
            pVo.setPlan(planName);
            pVo.setPlanId(planId);
            pVo.setUdf1(planId.toString());
            pVo.setUdf3(PaymentType.BUY_PLAN.name());
        } else if(type == PaymentType.BUY_TOPUP && amount > 0){
            pVo.setAmount(amount);
            pVo.setPlan("");
//            pVo.setPlanId();
//            pVo.setUdf1();
            pVo.setUdf3(PaymentType.BUY_TOPUP.name());
        } else
            params.put("fMessage", "Payment validation failed");
        pVo.setUdf2(txnid);
        pVo.setUdf4(byUserId.toString());

        params.put("paymentObj", pVo);
        return params;
    }
    public static String getURLWithContextPath(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return baseURL;
    }
    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }
        }catch(NoSuchAlgorithmException nsae){ }
        return hexString.toString();
    }
    public boolean empty(String s){
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    @Override
    public void processPayment(Map<String, String> params) {
        String base_url = "";
        String merchant_key = "";
        String salt = "";
        if( payuMoneyMode == "live"){
            base_url = payuMoneyLiveUrl;
            merchant_key = payuMoneyLiveKey;
            salt = payuMoneyLiveSalt;
        } else {
            base_url = payuMoneyTestUrl;
            merchant_key = payuMoneyTestKey;
            salt = payuMoneyTestSalt;
        }

        String action1 ="";
        int error=0;
        StringBuilder hashString = new StringBuilder();
        String txnid ="";
        if(empty(params.get("txnid"))){
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
            txnid=hashCal("SHA-256",rndm).substring(0,20);
        } else {
            txnid=params.get("txnid");
        }

        String udf2 = txnid;
        String hash="";

        if(empty(params.get("hash")) && params.size()>0){
            if( empty(params.get("key"))
                    || empty(params.get("txnid"))
                    || empty(params.get("amount"))
                    || empty(params.get("phone"))
                    || empty(params.get("surl"))
                    || empty(params.get("furl"))
                    || empty(params.get("serviceProvider"))
                    )

                error=1;
            else{
                String[] hashVarSeq=hashSequence.split("\\|");

                for(String part : hashVarSeq) {
                    if (empty(params.get(part)))
                        hashString.append("");
                    else
                        hashString.append(params.get(part));
                    hashString.append("|");
                }
                hashString.append(salt);

                hash=hashCal("SHA-512",hashString.toString());
                action1=base_url.concat("/_payment");
            }
        } else if(!empty(params.get("hash"))){
            hash=params.get("hash");
            action1=base_url.concat("/_payment");
        }
        params.put("hash", hash);
        params.put("actionPUM", action1);
    }

    public Model responsePayment(Map<String, String> params, Model model) {

        String base_url = "";
        String key = "";
        String salt = "";
        if( payuMoneyMode == "live"){
            base_url = payuMoneyLiveUrl;
            key = payuMoneyLiveKey;
            salt = payuMoneyLiveSalt;
        } else {
            base_url = payuMoneyTestUrl;
            key = payuMoneyTestKey;
            salt = payuMoneyTestSalt;
        }

        String amount = params.get("amount");
        String productinfo= params.get("productinfo");
        String txnid = params.get("txnid");
        String phone = params.get("phone");
        String firstname = params.get("firstname");

        String status = params.get("status");
        String r_h =params.get("hash");
        String hashString="";
        String udf1 =params.get("udf1");
        String udf2 =params.get("udf2");
        String udf3 =params.get("udf3");
        String udf4 =params.get("udf4");
        String udf5 =params.get("udf5");
        String p_Id = params.get("payuMoneyId");

        String additionalCharges = params.get("additionalCharges");
        float totalPaid =  Float.valueOf(amount.trim()).floatValue() +  Float.valueOf(additionalCharges.trim()).floatValue();

        String uid = udf4;

        /*// SAVE order To DB
        String db_user = username;
        String db_pass = password;
        String db_driver = driverClass;
        String db_con_url = connectionURL;

        Connection connection = null;
        PreparedStatement pstatement = null;
        try{
            Class.forName(db_driver).newInstance();
            int updateQuery = 0;

            connection = DriverManager.getConnection(db_con_url,db_user,db_pass);
            String queryString = "INSERT INTO orders(uid,user_name, planId,couse_name,subscription_plan,plan_price,additional_charges,total_paid,payment_id,transaction_id, transaction_status)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //queryString =+ " VALUES ( '"+productinfo+"', '"+udf3+"', '"+amount+"', '"+additionalCharges+"', '"+totalPaid+"','"+p_Id+"','"+txnid+"','status')";

            pstatement = connection.prepareStatement(queryString);
            pstatement.setString(1, uid);
            pstatement.setString(2, firstname);
            pstatement.setString(3, udf1);
            pstatement.setString(4, productinfo);
            pstatement.setString(5, udf3);
            pstatement.setString(6, amount);
            pstatement.setString(7, additionalCharges);
            pstatement.setString(8, String.valueOf(totalPaid));
            pstatement.setString(9, p_Id);
            pstatement.setString(10, txnid);
            pstatement.setString(11, status);

            updateQuery = pstatement.executeUpdate();

        } catch (Exception e){
            //out.println("An exception occurred: " + e.getMessage());
        }*/

        model.addAttribute("p_Id",p_Id);
        model.addAttribute("status",status);
        model.addAttribute("amount",amount);
        model.addAttribute("additionalCharges",additionalCharges);
        model.addAttribute("txnid",txnid);
        model.addAttribute("productinfo",productinfo);
        model.addAttribute("udf3",udf3);

        String hash;
        String email = params.get("email");
        if(status.equals("success")){
            if(additionalCharges!=null) {
                String hashSequence = additionalCharges+"|"+salt+"|"+status+"||||||"+udf5+"|"+udf4+"|"+udf3+"|"+udf2+"|"+udf1+"|"+email+"|"+firstname+"|"+productinfo+"|"+amount+"|"+txnid+"|";
                hashString=hashSequence.concat(key);
                //out.println(hashString);
                hash=hashCal("SHA-512",hashString);
                //out.println(hash);
                model.addAttribute("hashSuccess",r_h.equals(hash));
                /*if(r_h.equals(hash))
                    out.println("<div class='forms_grp strong success'>Successfull with additional charges</div>");
                else
                    out.println("<div class='forms_grp strong red'>Failure</div>");
                */
            } else {
                String hashSequence = salt+"|"+status+"||||||"+udf5+"|"+udf4+"|"+udf3+"|"+udf2+"|"+udf1+"|"+email+"|"+firstname+"|"+productinfo+"|"+amount+"|"+txnid+"|";
                hashString=hashSequence.concat(key);
                //out.println(hashString);
                hash=hashCal("SHA-512",hashString);
                //out.println(hash);
                model.addAttribute("hashSuccess",r_h.equals(hash));
                /*if(r_h.equals(hash)){
                    out.println("<div class='pay_resp strong success'>Successfull</div>");
                } else{
                    out.println("<div class='pay_resp strong red'>Failure</div>");
                }*/
            }
        }
        return model;
    }
}
