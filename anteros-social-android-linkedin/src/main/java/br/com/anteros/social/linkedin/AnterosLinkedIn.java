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

package br.com.anteros.social.linkedin;

import android.app.Activity;
import android.content.Intent;

import br.com.anteros.social.core.AnterosSocialConfiguration;
import br.com.anteros.social.core.AnterosSocialNetwork;
import br.com.anteros.social.core.AnterosSocialSession;
import br.com.anteros.social.core.OnLoginListener;
import br.com.anteros.social.core.OnLogoutListener;
import br.com.anteros.social.core.OnProfileListener;

/**
 * Created by edson on 10/04/16.
 */
public class AnterosLinkedIn implements AnterosSocialNetwork {

    private AnterosSocialSession session = null;

    private AnterosLinkedIn() {
    }

    public static AnterosLinkedIn create(Activity activity, OnLoginListener onLoginListener, OnLogoutListener onLogoutListener) {
        return new AnterosLinkedIn(activity,onLoginListener,onLogoutListener);
    }

    public static AnterosLinkedIn create(AnterosSocialConfiguration configuration) {
        return new AnterosLinkedIn(configuration);
    }

    private AnterosLinkedIn(Activity activity, OnLoginListener onLoginListener, OnLogoutListener onLogoutListener) {
        this(new AnterosLinkedInConfiguration.Builder().activity(activity).onLoginListener(onLoginListener).onLogoutListener(onLogoutListener).build());
    }

    private AnterosLinkedIn(AnterosSocialConfiguration configuration) {
        session = new AnterosLinkedInSession((AnterosLinkedInConfiguration) configuration);
    }
    
    @Override
    public void silentLogin() {
        checkListeners();
        session.silentLogin();
    }

    @Override
    public void login() {
        checkListeners();
        session.login();
    }

    @Override
    public void logout() {
        checkListeners();
        session.logout();
    }

    @Override
    public void revoke() {
        checkListeners();
        session.revoke();
    }

    private void checkListeners() {
        session.checkListeners();
    }

    @Override
    public boolean isLogged() {
        return session.isLogged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        session.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public AnterosSocialSession getSession() {
        return session;
    }

    @Override
    public void getProfile(OnProfileListener onProfileListener) {
        session.getProfile(onProfileListener);
    }

    @Override
    public void setOnLoginListener(OnLoginListener onLoginListener) {
       session.setOnLoginListener(onLoginListener);
    }

    @Override
    public void setOnLogoutListener(OnLogoutListener onLogoutListener) {
       session.setOnLogoutListener(onLogoutListener);
    }
}
