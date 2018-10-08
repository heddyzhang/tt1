package kit.juchu.JuchuAddOnly;

import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受注No採番クライアントクラスです。
 * Singleton パターン利用。
 */
public class JuchuJuchuAddOnly_NumberClient extends KitService {
	/* シリアルID */
	private static final long serialVersionUID = 4452577495317229560L;

	private static JuchuJuchuAddOnly_NumberClient numberClient;
    private static Object lockObj = new Object();

    // 他のクラスからの生成を防ぎます。
    private JuchuJuchuAddOnly_NumberClient() {
    }

    /**
     * JuchuJuchuAddOnly_NumberClientの唯一のインスタンスを返します。
     * 他のクラスからのインスタンス生成を制御します。
     * @return JuchuJuchuAddOnly_NumberClient
     */
    public static JuchuJuchuAddOnly_NumberClient getInstance() {
        synchronized(lockObj) {
            if (numberClient == null) {
                numberClient = new JuchuJuchuAddOnly_NumberClient();
            }
            return numberClient;
        }
    }

    /**
     * 枝番をインクリメントした受注NOを取得します。
     * @param kaisyaCd 会社CD
     * @param baseJyucyuNo ベースとなる受注NO
     * @return 枝番をインクリメントした受注NO
     */
    public String getIncrementedJyucyuNo(String kaisyaCd, String baseJyucyuNo) {
        synchronized(this) {
        	JuchuJuchuAddOnly_SearchService searchServ = new JuchuJuchuAddOnly_SearchService(
    				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

        	// 枝番をインクリメントした受注NOを取得
            String returnNumber = "";
    		int maxRetry = 9; // 最大リトライ回数
    		for (int i = 1; i <= maxRetry; i++) {
    			// 次の枝番を取得
    			String nextEdaban = searchServ.getNextEdaban(kaisyaCd, baseJyucyuNo);

    			// 受注NOの生成
    			StringBuilder sb = new StringBuilder();
    			sb.append(baseJyucyuNo.substring(0, 7)); // ベースとなる受注NOの前7桁
    			sb.append(nextEdaban); // 次の枝番

    			returnNumber = sb.toString();

    			// 既にあったら初期化してリトライ、なければ採番終了して戻る
    			if (searchServ.isExistJuchuNo(kaisyaCd, returnNumber)) {
    				returnNumber = "";
    			} else {
    				break;
    			}
    		}

    		return returnNumber;
        }
    }

    /**
     * XXX:【テスト用】受注NOの自動採番
     *
     * @param db_
     * @param symbol
     * @param online
     * @return returnNumber
     */
    public String getNextJyucyuNoForTest(PbsDatabase db_, String symbol, String online) {
        synchronized(this) {
            String returnNumber = "";
            String renban = "0";

    		String searchSql = "SELECT SEQ_TEST_NO.NEXTVAL AS NEXTSEQNO FROM DUAL";
    		Integer iCc = db_.prepare(true, searchSql);
    		PbsRecord[] records_ = null;
    		try {
    			db_.execute(iCc);
    			records_ = db_.getPbsRecords(iCc);
    		} catch (Exception e) {
    		}
    		if (records_ != null) {
    			renban = records_[0].getString("NEXTSEQNO");
    		}

    		returnNumber = symbol + online + PbsUtil.getCode(renban, 5) + "0";

    		return returnNumber;
        }
    }
}
