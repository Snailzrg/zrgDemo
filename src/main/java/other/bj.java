package other;

public class bj {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String ss = "[2019-10-16 15:44:25,514] INFO [Thread-76] ygfmis.fw.system.db.hibernate.YGSQLQuery - [Thread-76][0000]SQL: SELECT MSG_ID,"
				+ "MSG_CONSUME FROM MSG_QUEUE WHERE MSG_SUCCEED=0 AND MSG_KEY IS NULL"
				+ " AND ROWNUM <= 100  ORDER BY MSG_RECV+"
				+"[2019-10-16 15:44:25,514] INFO [Thread-76] com.ygsoft.ecp.service.datasource.DataSourceWrapper - 当前使用主数据源,名称为[fmismain]"
+"[2019-10-16 15:44:25,516] INFO [Thread-76] ygfmis.fw.system.dataset.FmisDataSet - [Thread-76]FmisDataSet.Open(con,sql):SELECT MSG_ID,MSG_CONSUM"
+" FROM MSG_QUEUE WHERE MSG_SUCCEED=0 AND MSG_KEY IS NULL AND ";
		String sss = "bbb";
				
				
		System.out.println(ss.hashCode());
		System.out.println(sss.hashCode());
	}

}
