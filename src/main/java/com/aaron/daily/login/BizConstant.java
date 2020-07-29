package com.aaron.daily.login;

/**
 * @description:    业务常量类
 * @author:         **gexiaobing**
 * @create_time:    2019/12/11 3:55 下午
 */
public interface BizConstant {

    /**
     * 鲲作网index prefix
     */
    String KZW_INDEX_PREFIX = "kzw_index_";

    /**
     * kics session id
     */
    String KICS_SESSION_ID = "sessionId";

    /**
     * kics loginname
     */
    String KICS_LOGIN_NAME = "loginname";

    /**
     * kzw session id
     */
    String KZW_SESSION_ID = "kzwSessionId";

    /**
     * kzw session(在kics中)超时时间 1小时,如果超时了从kics自动生成
     */
    int KZW_SESSION_TIMEOUT_IN_KICS = 60*60;

    /**
     * kzw session在缓存中的前缀
     */
    String KZW_SESSION_KEY_PREFIX = "session_";

    /**
     * 昵称名称长度
     */
    int NICK_NAME_LENGTH = 10;

    /**
     * 少量评论的数值
     */
    int SMALL_AMOUNT_COMMENT = 3;

    /**
     * es默认List为 10, 手动设置新的 默认值
     */
    int DEFAULT_ALL_LIST_SIZE = 1000;

}
