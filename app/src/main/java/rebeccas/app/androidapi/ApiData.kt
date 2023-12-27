package rebeccas.app.androidapi

import kotlinx.serialization.Serializable

@Serializable
data class ApiData(val categories: List<String>, val value: String)



