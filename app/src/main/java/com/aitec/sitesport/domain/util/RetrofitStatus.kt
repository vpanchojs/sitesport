import android.util.Log
import retrofit2.Response
import java.io.IOException

class RetrofitStatus {
    companion object {
        fun <T> Response(response: Response<T>, callback: MyCallbackResponse<T>) {
            val code = response.code()
            if (code in 200..299) {
                callback.success(response)
            } else if (code == 401) {
                callback.unauthenticated(response)
            } else if (code in 400..499) {
                callback.clientError(response)
            } else if (code in 500..599) {
                callback.serverError(response)
            } else {
                callback.unexpectedError(RuntimeException("Unexpected response $response"))
            }
        }

        fun <T> Failure(t: Throwable, callback: MyCallbackFailure<T>) {
            if (t is IOException) {
                Log.e("network", t.toString())
                callback.networkError("Verifique su conexion e intentlo de nuevo")
            } else {
                callback.unexpectedError(t)
            }
        }

    }

    interface MyCallbackResponse<T> {
        /**
         * Called for [200, 300) responses.
         */
        fun success(response: Response<T>)

        /**
         * Called for 401 responses.
         */
        fun unauthenticated(response: Response<*>)

        /**
         * Called for [400, 500) responses, except 401.
         */
        fun clientError(response: Response<*>)

        /**
         * Called for [500, 600) response.
         */
        fun serverError(response: Response<*>)

        /**
         * Called for unexpected errors while making the call.
         */
        fun unexpectedError(t: Throwable)
    }


    interface MyCallbackFailure<T> {

        /**
         * Called for network errors while making the call.
         */
        fun networkError(e: String)

        /**
         * Called for unexpected errors while making the call.
         */
        fun unexpectedError(t: Throwable)
    }
}