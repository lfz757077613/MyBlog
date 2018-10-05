package com.lfz.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 避免一次请求多次查session，SecurityUtils.getSubject().getSession()，将session放在request域中
 */
public class MySessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = ((WebSessionKey) sessionKey).getServletRequest();

        Session session;
        if (request != null && sessionId != null) {
            session = (Session) request.getAttribute(sessionId.toString());
            if (session != null) {
                return session;
            }
        }
        session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null && session != null) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
