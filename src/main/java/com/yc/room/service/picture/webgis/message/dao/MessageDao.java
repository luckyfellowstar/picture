package com.yc.room.service.picture.webgis.message.dao;


import com.yc.room.service.picture.webgis.message.model.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * messageDao
 */

public interface MessageDao extends JpaRepository<MessageInfo, Integer> {

    List<MessageInfo> findAll();

    MessageInfo findMessageInfoById(Integer id);


    List<MessageInfo> findAllByMarkAndCountLessThan(Integer mark, Integer count);

    @Query("update MessageInfo m set m.mark=1  where m.id=?1")
    @Modifying
    @Transactional
    Integer updateMark(Integer id);

}
