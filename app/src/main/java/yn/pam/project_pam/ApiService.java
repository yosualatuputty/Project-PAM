package yn.pam.project_pam;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("transaksi.php")
    Call<List<Item>> getItems(@Query("kategori") String kategori);
    Call<List<Item>> getDataByCategory(@Query("kategori") String kategori);
}