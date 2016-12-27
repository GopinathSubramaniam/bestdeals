package com.deals.controller;

import com.deals.model.PublicUserPlan;
import com.deals.model.User;
import com.deals.service.PublicUserPlanService;
import com.deals.service.UserService;
import com.deals.service.payment.PaymentService;
import com.deals.vo.PaymentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.awt.ModalExclude;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gsitgithub on 19/12/2016.
 */
@Controller
public class PaymentController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired private HttpSession session;
    @Autowired private UserService userService;
    @Autowired private PaymentService paymentService;
    @Autowired private PublicUserPlanService publicUserPlanservice;

    @RequestMapping(value="/initpayment")
    public String initPayment(HttpServletRequest req, Model model){
        Object objval = getSessionVal("userId");
        if (objval == null)
            return "redirect:/";
        String paymentType = req.getParameter("type");
        String planId = req.getParameter("planId");
        String amount = req.getParameter("amount");

        PaymentService.PaymentType type = null;
        if (PaymentService.PaymentType.BUY_PLAN.toString().equals(paymentType))
            type = PaymentService.PaymentType.BUY_PLAN;
        else if (PaymentService.PaymentType.BUY_TOPUP.toString().equals(paymentType))
            type = PaymentService.PaymentType.BUY_TOPUP;

        if (type != null) {
            Long loggedInUserId = (Long) objval;
            long longPlanId = (planId != null && !planId.isEmpty()) ? Long.parseLong(planId) : 0;
            double longAmount = (amount != null && !amount.isEmpty()) ? Double.parseDouble(amount) : 0;

            Map<String, Object> paymentAttributes = paymentService.initPayment(loggedInUserId, longPlanId, type, longAmount);
            model.addAttribute("payment", paymentAttributes.get("paymentObj") );
            model.addAttribute("message", paymentAttributes.get("fMessage"));
            return "payment/initPayment";
        }
        model.addAttribute("message", "Incorrect payment type");
        return "redirect:/";
    }

    @RequestMapping(value="/topup")
    public String topupPayment(HttpServletRequest req, Model model){
        Object objval = getSessionVal("userId");
        String paymentType = req.getParameter("type");
        String planId = req.getParameter("planId");
        String amount = req.getParameter("amount");
        //model.addAttribute("message", "Incorrect payment type");
        if (objval == null)
            return "redirect:/";

        return "payment/topup";
    }

    @RequestMapping(value="/processpayment")
    public String processPayment(HttpServletRequest req, Model model){
        Object objval = getSessionVal("userId");
        if (objval == null)
            return "redirect:/";
        String planId = req.getParameter("planId");
        String amount = req.getParameter("amount");
        String txnid = req.getParameter("txnid");
        String planName = req.getParameter("udf3");

        User paymentProcessorUser = userService.findOne((Long)objval);
        if (paymentProcessorUser == null)
            return "redirect:/";

        if (PaymentService.PaymentType.BUY_TOPUP.name().equals(planName)) {
            PublicUserPlan publicUserPlan = publicUserPlanservice.findByUserId(paymentProcessorUser.getId());
            publicUserPlan.setAmount(publicUserPlan.getAmount() + Double.parseDouble(amount));
            publicUserPlanservice.updatePublicUserPlan(publicUserPlan);
        }
        model.addAttribute("amount",amount);
        model.addAttribute("txnid",txnid);
//        model.addAttribute("udf2",udf2);
//        model.addAttribute("serviceProvider","payu_paisa");
//        model.addAttribute("planId",planId);
//        model.addAttribute("plan",planName);
//        model.addAttribute("phone",phone);
//        model.addAttribute("udf1", planId);
//        model.addAttribute("udf3", planName);
//        model.addAttribute("surl", successUrl);
//        model.addAttribute("furl", failureUrl);
//        model.addAttribute("udf4", userId);

        model.addAttribute("p_Id", "TODO");
        model.addAttribute("additionalCharges", "No additional charges");
        model.addAttribute("status", "Successful");
        model.addAttribute("udf3", planName);
        model.addAttribute("productinfo", planName);
        model.addAttribute("message", "Payment successful");
        return "payment/responsePayment";
    }

    public Object getSessionVal(String key){
        Object val = session.getAttribute(key);
        if(val != null){
            String  strVal = val.toString();
            val = strVal;
            if(key.equals("userId")){
                val = Long.parseLong(strVal);
            }
        }
        return val;
    }
}
