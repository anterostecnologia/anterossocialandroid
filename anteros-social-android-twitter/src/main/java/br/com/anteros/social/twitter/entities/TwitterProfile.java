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

package br.com.anteros.social.twitter.entities;

import android.graphics.Bitmap;

import br.com.anteros.social.core.AgeRange;
import br.com.anteros.social.core.SocialNetworkType;
import br.com.anteros.social.core.SocialProfile;

/**
 * Created by edson on 28/03/16.
 */
public class TwitterProfile implements SocialProfile {

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    private String birthday;

    private String email;

    private String image;

    private String link;
    private Bitmap imageBitmap;

    public String getLink() {
        return link;
    }

    public TwitterProfile(String firstName, String middleName, String lastName, String gender, String birthday, String email, String image, String link, TwitterAgeRange ageRange) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.image = image;
        this.link = link;
        this.ageRange = ageRange;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(TwitterAgeRange ageRange) {
        this.ageRange = ageRange;
    }

    private TwitterAgeRange ageRange;


    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getUserName() {
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFullName() {
        return null;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getImageUrl() {
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TwitterProfile{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", ageRange=" + ageRange +
                '}';
    }

    public Bitmap getImageBitmap(){
        return this.imageBitmap;
    }

    @Override
    public SocialNetworkType getProfileType() {
        return SocialNetworkType.TWITTER;
    }

    @Override
    public String getProfileName() {
        return "Twitter";
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageBitmap = bitmap;
    }
}
