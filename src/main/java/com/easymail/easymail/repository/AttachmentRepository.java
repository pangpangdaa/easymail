package com.easymail.easymail.repository;

import com.easymail.easymail.entity.Attachment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttachmentRepository extends MongoRepository<Attachment,String> {
}
