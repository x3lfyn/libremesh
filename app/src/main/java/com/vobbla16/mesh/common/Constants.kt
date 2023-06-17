package com.vobbla16.mesh.common

object Constants {
    const val DEFAULT_STRING = "???"
    const val DEFAULT_ERROR_MESSAGE = "Some error occurred"

    const val MOSRU_OAUTH_URL = "https://login.mos.ru/sps/oauth/ae?response_type=code&access_type=offline&client_id=dnevnik.mos.ru&scope=openid+profile+birthday+contacts+snils+blitz_user_rights+blitz_change_password&redirect_uri=https://school.mos.ru/v3/auth/sudir/callback"
    const val OAUTH_CALLBACK_PREFIX = "https://school.mos.ru/v3/auth/sudir/callback?code="
    const val OAUTH_CODE_TO_AUPD_URL = "https://school.mos.ru/v3/auth/sudir/callback"
    const val AUPD_TO_TOKEN_URL = "https://school.mos.ru/v2/token/refresh?roleId=1&subsystem=2"
}