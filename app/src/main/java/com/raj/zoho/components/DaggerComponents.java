package com.raj.zoho.components;

import com.raj.zoho.activities.home.HomeActivity;
import com.raj.zoho.activities.home.HomeMod;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = HomeMod.class)
public interface DaggerComponents {
    void inject(HomeActivity mainActivity);
}
