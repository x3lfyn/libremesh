package com.vobbla16.mesh.common

object Constants {
    const val DEFAULT_STRING = "???"
    const val DEFAULT_ERROR_MESSAGE = "Some error occurred"

    const val MESH_API_BASE_DOMAIN_DNEVNIK = "https://dnevnik.mos.ru"
    const val MESH_API_BASE_DOMAIN_SCHOOL = "https://school.mos.ru"

    const val MOSRU_OAUTH_URL = "https://login.mos.ru/sps/oauth/ae?response_type=code&access_type=offline&client_id=dnevnik.mos.ru&scope=openid+profile+birthday+contacts+snils+blitz_user_rights+blitz_change_password&redirect_uri=https://school.mos.ru/v3/auth/sudir/callback"
    const val OAUTH_CALLBACK_PREFIX = "https://school.mos.ru/v3/auth/sudir/callback?code="
    const val OAUTH_CODE_TO_AUPD_URL = "https://school.mos.ru/v3/auth/sudir/callback"
    const val AUPD_TO_TOKEN_URL = "https://school.mos.ru/v2/token/refresh?roleId=1&subsystem=2"

    const val ACADEMIC_YEARS_ENDPOINT = "/api/ej/core/family/v1/academic_years"
    const val PROFILE_ENDPOINT = "/api/family/web/v1/profile"
    const val MARKS_ENDPOINT = "/api/ej/report/family/v1/progress/json"
    const val SCHEDULE_ENDPOINT = "/api/family/web/v1/schedule"
    const val LESSON_INFO_ENDPOINT = "/api/family/web/v1/lesson_schedule_items/"
    const val CLASSMATES_ENDPOINT = "/core/api/profiles"
    const val RATING_CLASS_ENDPOINT = "/api/ej/rating/v1/rank/class"
    const val SHORT_SCHEDULE_ENDPOINT = "/api/family/web/v1/schedule/short"
    const val MARK_INFO_ENDPOINT = "/api/family/web/v1/marks/"
    const val HOMEWORK_DONE_ENDPOINT = "/api/family/web/v1/homeworks/"
    const val HOMEWORK_DONE_ENDPOINT_END = "/done"
}