//package demo;
//
//import java.rmi.RemoteException;
//import javax.xml.rpc.ParameterMode;
//import javax.xml.rpc.ServiceException;
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.encoding.XMLType;
//
//public class WebService {
//	/**
//	 * 使用webservice获取网页内容（参数暂时只支持String类型） !!参数和参数名需一一对应!!
//	 * 
//	 * @Title: getWebserviceContent
//	 * @Description: TODO
//	 * @param endpoint
//	 *            远程调用路径
//	 * @param method
//	 *            调用的方法名
//	 * @param params
//	 *            参数
//	 * @param paramsName
//	 *            参数名
//	 * @return 正常情况返回JSON类型字符串，异常情况返回"error"
//	 */
//	public static String getWebserviceContent(String endpoint, String method, Object[] params, String[] paramsName) {
//		String result = "error";
//		// 参数及参数名是否符合要求判断
//		if (params == null || paramsName == null || params.length != paramsName.length) {
//			return result;
//		}
//		Service service = new Service();
//		Call call;
//		try {
//			call = (Call) service.createCall();
//			call.setTargetEndpointAddress(endpoint);// 远程调用路径
//			call.setOperationName(method);// 调用的方法名
//			// 设置参数名:
//			for (String paramName : paramsName) {
//				// 参数名、参数类型:String、 参数模式：'IN' or 'OUT'
//				call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);
//			}
//			// 设置返回值类型：
//			call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
//			result = (String) call.invoke(params);// 远程调用
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//}
