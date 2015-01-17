package com.eltacodeldiablo.web.session;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {

	private Map<String, Object>	flashData	= new HashMap<>();

	public void addFlashData(String key, Object value) {
		flashData.put(key, value);
	}

	public Object getFlashData(String key) {
		Object object = flashData.get(key);
		flashData.remove(key);
		return object;
	}
}
