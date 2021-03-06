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

package br.com.anteros.social.google;

import android.app.Activity;

import java.lang.ref.WeakReference;

import br.com.anteros.social.core.AnterosSocialConfiguration;
import br.com.anteros.social.core.OnLoginListener;
import br.com.anteros.social.core.OnLogoutListener;
import br.com.anteros.social.core.SocialNetworkType;

/**
 * Created by edson on 25/03/16.
 */
public class AnterosGoogleConfiguration implements AnterosSocialConfiguration {

    private WeakReference<OnLoginListener> onLoginListener;
    private WeakReference<OnLogoutListener> onLogoutListener;
    private WeakReference<Activity> activity;

    public OnLoginListener getOnLoginListener() {
        return onLoginListener.get();
    }

    public OnLogoutListener getOnLogoutListener() {
        return onLogoutListener.get();
    }

    public Activity getActivity() {
        return activity.get();
    }

    private AnterosGoogleConfiguration(Builder builder) {
        this.onLoginListener = new WeakReference<>(builder.onLoginListener);
        this.onLogoutListener = new WeakReference<>(builder.onLogoutListener);
        this.activity = new WeakReference<>(builder.activity);
    }

    @Override
    public SocialNetworkType getSocialNetworkType() {
        return SocialNetworkType.GOOGLE;
    }


    public static class Builder {

        private OnLoginListener onLoginListener;
        private OnLogoutListener onLogoutListener;
        private Activity activity;


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


        public AnterosGoogleConfiguration build() {
            return new AnterosGoogleConfiguration(this);
        }

    }


}
