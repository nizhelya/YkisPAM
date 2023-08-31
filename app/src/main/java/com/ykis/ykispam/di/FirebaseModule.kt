/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ykis.ykispam.R
import com.ykis.ykispam.core.Constants.SIGN_IN_REQUEST
import com.ykis.ykispam.core.Constants.SIGN_UP_REQUEST
import com.ykis.ykispam.firebase.model.service.impl.ConfigurationServiceImpl
import com.ykis.ykispam.firebase.model.service.impl.FirebaseServiceImpl
import com.ykis.ykispam.firebase.model.service.impl.LogServiceImpl
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.reply.data.AccountsRepository
import com.ykis.ykispam.reply.data.AccountsRepositoryImpl
import com.ykis.ykispam.reply.data.EmailsRepository
import com.ykis.ykispam.reply.data.EmailsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
//@InstallIn(ViewModelComponent::class)

object FirebaseModule {
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    fun firestore(): FirebaseFirestore = Firebase.firestore


    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideFirebaseService(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInClient: GoogleSignInClient,
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): FirebaseService = FirebaseServiceImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )



    @Provides
    fun provideLogService(): LogService = LogServiceImpl()
    @Provides
    fun provideEmailsRepository(): EmailsRepository = EmailsRepositoryImpl()

    @Provides
    fun provideAccountRepository(): AccountsRepository = AccountsRepositoryImpl()

    @Provides
    fun provideConfigurationService(): ConfigurationService = ConfigurationServiceImpl()


}