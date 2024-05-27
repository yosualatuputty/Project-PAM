//package yn.pam.project_pam.db;
//
//import android.content.Context;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = {Transaction.class}, version = 1)
//public abstract class TransactionDatabase extends RoomDatabase {
//    private static TransactionDatabase INSTANCE;
//    public abstract TransactionDAO transactionDAO();
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
