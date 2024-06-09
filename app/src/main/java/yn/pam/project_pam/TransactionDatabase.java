//package yn.pam.project_pam;
//
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
//public abstract class TransactionDatabase extends RoomDatabase {
//    public abstract TransactionDao transactionDao();
//
//    private static volatile TransactionDatabase INSTANCE;
//
//    public static TransactionDatabase getInstance(Context context) {
//        if (INSTANCE == null) {
//            synchronized (TransactionDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    TransactionDatabase.class, "transaction_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
//
//
