package cn.dev33.satoken.context;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.exception.SaTokenException;

/**
 * 基于ThreadLocal的上下文对象存储器 
 * @author kong
 *
 */
public class SaTokenContextForThreadLocalStorage {
	
	/**
	 * 基于 ThreadLocal 的上下文 
	 */
	static ThreadLocal<Box> boxThreadLocal = new InheritableThreadLocal<Box>();
	
	/**
	 * 初始化 [容器]
	 * @param request {@link SaRequest}
	 * @param response {@link SaResponse}
	 * @param storage {@link SaStorage}
	 */
	public static void setBox(SaRequest request, SaResponse response, SaStorage storage) {
		Box bok = new Box(request, response, storage);
		boxThreadLocal.set(bok);
	};

	/**
	 * 清除 [容器]
	 */
	public static void clearBox() {
		boxThreadLocal.remove();
	};

	/**
	 * 获取 [容器]
	 * @return see note
	 */
	public static Box getBox() {
		return boxThreadLocal.get();
	};
	
	/**
	 * 获取 [容器], 如果为空则抛出异常 
	 * @return see note
	 */
	public static Box getBoxNotNull() {
		Box box = boxThreadLocal.get();
		if(box ==  null) {
			throw new SaTokenException("未成功初始化上下文");
		}
		return box;
	};

	/**
	 * 在 [上下文容器] 获取 [Request] 对象
	 * 
	 * @return see note 
	 */
	public static SaRequest getRequest() {
		return getBoxNotNull().getRequest();
	}

	/**
	 * 在 [上下文容器] 获取 [Response] 对象
	 * 
	 * @return see note 
	 */
	public static SaResponse getResponse() {
		return getBoxNotNull().getResponse();
	}

	/**
	 * 在 [上下文容器] 获取 [存储器] 对象 
	 * 
	 * @return see note 
	 */
	public static SaStorage getStorage() {
		return getBoxNotNull().getStorage();
	}

	
	/**
	 * 临时内部类，存储三个对象 
	 * @author kong
	 */
	/**
	 * @author kong
	 *
	 */
	public static class Box {
		
		public SaRequest request;
		
		public SaResponse response;
		
		public SaStorage storage;
		
		public Box(SaRequest request, SaResponse response, SaStorage storage){
			this.request = request;
			this.response = response;
			this.storage = storage;
		}

		public SaRequest getRequest() {
			return request;
		}

		public void setRequest(SaRequest request) {
			this.request = request;
		}

		public SaResponse getResponse() {
			return response;
		}

		public void setResponse(SaResponse response) {
			this.response = response;
		}

		public SaStorage getStorage() {
			return storage;
		}

		public void setStorage(SaStorage storage) {
			this.storage = storage;
		}

		@Override
		public String toString() {
			return "Box [request=" + request + ", response=" + response + ", storage=" + storage + "]";
		}
		
	}
	
}
