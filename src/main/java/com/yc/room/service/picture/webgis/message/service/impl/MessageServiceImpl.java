package com.yc.room.service.picture.webgis.message.service.impl;


import com.yc.room.service.picture.webgis.message.dao.MessageDao;
import com.yc.room.service.picture.webgis.message.model.MessageInfo;
import com.yc.room.service.picture.webgis.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class MessageServiceImpl implements MessageService {

    static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);


    @Autowired
    MessageDao messageDao;


    @Override
    public List<MessageInfo> findAll() {
        return messageDao.findAll();
    }

    @Override
    public MessageInfo findOneById(Integer id) {
        return messageDao.findMessageInfoById(id);
    }

    @Override
    public void save(MessageInfo messageInfo) {
        messageDao.save(messageInfo);
    }

    @Override
    public void sendMessage(MessageInfo message) throws Exception {

        log.info("修改发送短信内容成功");

    }


    @Override
    public List<MessageInfo> findResend() {
        return messageDao.findAllByMarkAndCountLessThan(1, 3);
    }

    @Override
    public Integer updateMark(Integer id) {
        return messageDao.updateMark(id);
    }


}
