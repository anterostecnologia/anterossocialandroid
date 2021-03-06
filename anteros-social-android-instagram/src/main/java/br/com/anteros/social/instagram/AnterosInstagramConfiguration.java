/*
 * ******************************************************************************
 *  * Copyright 2016 Anteros Tecnologia
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package br.com.anteros.social.instagram;

import android.app.Activity;

import java.lang.ref.WeakReference;

import br.com.anteros.social.core.AnterosSocialConfiguration;
import br.com.anteros.social.core.OnLoginListener;
import br.com.anteros.social.core.OnLogoutListener;
import br.com.anteros.social.core.SocialNetworkType;

/**
 * Created by edson on 05/04/16.
 */
public class AnterosInstagramConfiguration implements AnterosSocialConfiguration {

    private WeakReference<OnLoginListener> onLoginListener;
    private WeakReference<OnLogoutListener> onLogoutListener;
    private WeakReference<Activity> activity;
    private String clientId;
    private String clientSecret;
    private String redirectURL;
    private String scope;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public String getScope() {
        return scope;
    }

    public OnLoginListener getOnLoginListener() {
        return onLoginListener.get();
    }

    public OnLogoutListener getOnLogoutListener() {
        return onLogoutListener.get();
    }

    public Activity getActivity() {
        return activity.get();
    }

    private AnterosInstagramConfiguration(Builder builder) {
        this.onLoginListener = new WeakReference<>(builder.onLoginListener);
        this.onLogoutListener = new WeakReference<>(builder.onLogoutListener);
        this.activity = new WeakReference<>(builder.activity);
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.scope = builder.scope;
        this.redirectURL = builder.redirectURL;
    }

    @Override
    public SocialNetworkType getSocialNetworkType() {
        return SocialNetworkType.INSTAGRAM;
    }


    public static class Builder {

        private OnLoginListener onLoginListener;
        private OnLogoutListener onLogoutListener;
        private Activity activity;
        private String clientId;
        private String clientSecret;
        private String redirectURL;
        private String scope;


        public Builder() {
        }

        public Builder onLoginListener(OnLoginListener onLoginListener){
            this.onLoginListener = onLoginListener;
            return this;
        }

        public Builder onLogoutListener(OnLogoutListener onLogoutListener){
            this.onLogoutListener = onLogoutListener;
            return this;
        }

        public Builder activity(Activity activity){
            this.activity = activity;
            return this;
        }
        public Builder clientId(String clientId){
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret){
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectURL(String redirectURL){
            this.redirectURL = redirectURL;
            return this;
        }

        public Builder scope(String scope){
            this.scope = scope;
            return this;
        }


        public AnterosInstagramConfiguration build() {
            return new AnterosInstagramConfiguration(this);
        }

    }
}
