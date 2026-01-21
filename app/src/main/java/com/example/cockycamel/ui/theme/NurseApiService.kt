import com.example.cockycamel.ui.Nurse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface NurseApiService {
    @GET("enfermeros")
    suspend fun getEnfermeros(): List<Nurse>

}

object NurseApi {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val retrofitService: NurseApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NurseApiService::class.java)
    }
}