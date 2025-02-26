package com.pm.pmapp.service;

import com.pm.pmapp.model.PlanType;
import com.pm.pmapp.model.Subscription;
import com.pm.pmapp.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType planType);
    boolean isValid(Subscription subscription);

}
