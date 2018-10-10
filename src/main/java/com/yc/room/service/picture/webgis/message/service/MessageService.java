package com.yc.room.service.picture.webgis.message.service;



import com.yc.room.service.picture.webgis.message.model.MessageInfo;

import java.util.List;

/**
 * 消息管理
 */
public interface MessageService {


    /**
     * 查询所有发送消息
     *
     * @return
     */
    List<MessageInfo> findAll();

    /**
     * 根据id查询消息
     *
     * @param id
     * @return
     */
    MessageInfo findOneById(Integer id);

    /**
     * 保存消息体
     *
     * @param messageInfo
     */
    void save(MessageInfo messageInfo);

    /**
     * 发送短信
     * @param message
     */
    void sendMessage(MessageInfo message)throws Exception;

    /**
     * 获取需要重新发送的短信
     *
     * @return
     */
    List<MessageInfo> findResend();

    /**
     * 修改标示位
     *
     * @param id
     * @return
     */
    Integer updateMark(Integer id);

}
