package com.aitec.sitesport.domain.util

/**
 * Created by victor on 24/1/18.
 */
class MapperResponse {
/*
    companion object {
        fun getUser(jsonObject: JSONObject): User {
            var userJson = jsonObject.getJSONObject("user")
            val user = User()
            user.email = userJson.getString("email")
            user.idUser = userJson.getString("pk")
            user.photo = userJson.getString("foto")
            user.userName = userJson.getString("username")
            user.names = userJson.getString("nombres")
            return user
        }

        fun getToken(jsonObject: JSONObject): String {
            return jsonObject.getString("token")
        }

        fun getResultSearch(jsonObject: JSONObject): SearchUserOrEntreprise {
            var resultsList = ArrayList<User>()
            for (i in 0..(jsonObject.getJSONArray("results").length() - 1)) {
                var obj = jsonObject.getJSONArray("results").getJSONObject(i)
                val user = User()
                user.idUser = obj.getString("pk")
                user.photo = obj.getString("foto")
                user.url_detail = obj.getString("url")
                user.userName = obj.getString("username")
                user.names = obj.getString("nombres")

                resultsList.add(user)
            }

            return SearchUserOrEntreprise(
                    jsonObject.getInt("previous"),
                    jsonObject.getInt("next"),
                    resultsList,
                    jsonObject.getInt("count"))
        }
    }

*/
}