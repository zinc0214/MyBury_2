package com.zinc.waver.ui.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zinc.datastore.login.LoginPreferenceDataStoreModule
import com.zinc.domain.usecases.login.LoginByEmail
import com.zinc.waver.ui.viewmodel.CommonViewModel
import com.zinc.waver.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinEmailViewModel @Inject constructor(
    private val loginByEmail: LoginByEmail,
    private val loginPreferenceDataStoreModule: LoginPreferenceDataStoreModule,
) : CommonViewModel() {

    private val _failEmailCheck = SingleLiveEvent<Boolean>()
    val failEmailCheck: LiveData<Boolean> get() = _failEmailCheck

    private val _goToMakeNickName = SingleLiveEvent<String>()
    val goToMakeNickName: LiveData<String> get() = _goToMakeNickName

    private val _isAlreadyUsedEmail = SingleLiveEvent<Boolean>()
    val isAlreadyUsedEmail: LiveData<Boolean> get() = _isAlreadyUsedEmail

    private val _goToLogin = MutableLiveData<Boolean>()
    val goToLogin: LiveData<Boolean> get() = _goToLogin

    fun goToLogin(email: String) {
        viewModelScope.launch(ceh(_failEmailCheck, true)) {
            runCatching {
                val res = loginByEmail(email)
                if (res.success) {
                    res.data?.let { token ->
                        loginPreferenceDataStoreModule.setAccessToken("Bearer ${token.accessToken}")
                        loginPreferenceDataStoreModule.setRefreshToken("Bearer ${token.refreshToken}")
                    }
                    _isAlreadyUsedEmail.value = true
                } else {
                    _goToMakeNickName.value = email
                }
            }.getOrElse {
                _failEmailCheck.value = true
            }
        }
    }

    fun savedLoginEmail(email: String) {
        viewModelScope.launch {
            loginPreferenceDataStoreModule.setLoginEmail(email)
            _goToLogin.value = true
        }
    }
}