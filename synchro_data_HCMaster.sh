#!/bin/sh
#本番DB同期用シェル

USER_HOME=$HOME
. ${USER_HOME}/.envora

# 同期前 子レコード処理 RMS
echo ■制約無効
sqlplus rms/$RMS_DEV_PASSWORD@$DB_SID_DEV @r_disable_rms.sql
# RMSの同期対象テーブル分ループ
ateYmd=`date '+%Y%m%d'`
echo ■$dateYmd 同期実行開始
for TABLE_NAME in UTLZ_EMP_ORG UTLZ_EMP_DAY_CC_TASK_HRS EMP_LIST ORG_LIST
do
echo $TABLE_NAME START
expdp rms/$RMS_PASSWORD@$DB_SID directory=CONSULT_DIR dumpfile=RMS_$TABLE_NAME.dmp tables=rms.$TABLE_NAME logfile=RMS_$TABLE_NAME_$dateYmd.export.log CONTENT=data_only
impdp rms/$RMS_DEV_PASSWORD@$DB_SID_DEV directory=CONSULT_DIR dumpfile=RMS_$TABLE_NAME.dmp tables=rms.$TABLE_NAME table_exists_action=TRUNCATE logfile=RMS_$TABLE_NAME_$DATEYmd.export.log
sqlplus -s rms/$RMS_DEV_PASSWORD@$DB_SID_DEV << EOF
begin

 utl_file.fremove('CONSULT_DIR','RMS_$TABLE_NAME.dmp');
 utl_file.fremove('CONSULT_DIR','RMS_$TABLE_NAME_$dateYmd.export.log');

end;
/
exit
/
EOF
echo $TABLE_NAME END
done

echo ■制約再開
sqlplus rms/$RMS_DEV_PASSWORD@$DB_SID_DEV @r_enable_rms.sql
