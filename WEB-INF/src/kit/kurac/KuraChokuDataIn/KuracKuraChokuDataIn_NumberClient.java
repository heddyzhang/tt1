package kit.kurac.KuraChokuDataIn;

import fb.com.ComDecode;
import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

/**
 * 蔵元直送入力クライアントクラスです。
 * Singleton パターン利用。確定時に取得。
 */
public class KuracKuraChokuDataIn_NumberClient extends KitService {

	/* シリアルID */
	private static final long serialVersionUID = -5728601663266375423L;


	private static KuracKuraChokuDataIn_NumberClient numberClient;
    private static Object lockObj = new Object();

    // 他のクラスからの生成を防ぎます。
    private KuracKuraChokuDataIn_NumberClient() {
    }

    /**
     * KeiriSogoSiirehinHenpinDataIn_NumberClientの唯一のインスタンスを返します。
     * 他のクラスからのインスタンス生成を制御します。
     * @return KeiriSogoSiirehinHenpinDataIn_NumberClient
     */
    public static KuracKuraChokuDataIn_NumberClient getInstance() {
        synchronized(lockObj) {
            if (numberClient == null) {
                numberClient = new KuracKuraChokuDataIn_NumberClient();
            }
            return numberClient;
        }
    }

    /**
     * 蔵直ﾃﾞｰﾀ連番の自動採番
     *
     * @param db_ データベースオブジェクト
     * @param seq シーケンス名
     * @return returnNumber 型NUMBERの為0詰不可
     */
    public String getNextKuradataNO(PbsDatabase db_, String seq, int keta) {
        synchronized(this) {
    		return PbsUtil.getCode(ComDecode.getSequence(db_, seq), keta);
        }
    }

}
