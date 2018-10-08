package kit.juchu.TuminiDenpyoHako;

import fb.com.ComDecode;
import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

/**
 * 伝票No採番クライアントクラスです。
 * Singleton パターン利用。
 */
public class JuchuTuminiDenpyoHako_NumberClient extends KitService {
	/* シリアルID */
	private static final long serialVersionUID = -2874913351634536840L;

	private static JuchuTuminiDenpyoHako_NumberClient numberClient;
    private static Object lockObj = new Object();

    // 他のクラスからの生成を防ぎます。
    private JuchuTuminiDenpyoHako_NumberClient() {
    }

    /**
     * JuchuTuminiDenpyoHako_NumberClientの唯一のインスタンスを返します。
     * 他のクラスからのインスタンス生成を制御します。
     * @return JuchuTuminiDenpyoHako_NumberClient
     */
    public static JuchuTuminiDenpyoHako_NumberClient getInstance() {
        synchronized(lockObj) {
            if (numberClient == null) {
                numberClient = new JuchuTuminiDenpyoHako_NumberClient();
            }
            return numberClient;
        }
    }

    /**
     * 伝票NOの自動採番
     *
     * @param db_ データベースオブジェクト
     * @param seq シーケンス名
     * @param keta 伝票NOの0詰め桁数
     * @return returnNumber 指定桁数で0詰めした伝票NO
     */
    public String getNextDenNo(PbsDatabase db_, String seq, int keta) {
        synchronized(this) {
    		return PbsUtil.getCode(ComDecode.getSequence(db_, seq), keta);
        }
    }


}
