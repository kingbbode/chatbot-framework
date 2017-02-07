package com.kingbbode.bot.common.base.knowledge.brain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingbbode.bot.common.annotations.Brain;
import com.kingbbode.bot.common.annotations.BrainCell;
import com.kingbbode.bot.common.base.knowledge.component.KnowledgeComponent;
import com.kingbbode.bot.common.request.BrainRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by YG-MAC on 2017. 1. 26..
 */
@Brain
public class KnowledgeBrain {

    @Autowired
    private KnowledgeComponent knowledgeComponent;

    @BrainCell(key = "학습", explain = "명령어 학습시키기", example = "#학습 명령어 학습내용", min = 2)
    public String addKnowledge(BrainRequest brainRequest) throws JsonProcessingException {
        String[] commandLine = brainRequest.getContent().split(" ");
        String command = commandLine[0];
        StringBuffer message = new StringBuffer();
        for (int i = 1; i < commandLine.length; i++) {
            message.append(commandLine[i] + " ");
        }
        return knowledgeComponent.addKnowledge(command, message.toString());
    }

    @BrainCell(key = "까묵", explain = "학습 시킨 명령어 지우기", example = "#까묵 명령어", min = 1)
    public String forgetKnowledge(BrainRequest brainRequest) {
        return knowledgeComponent.forgetKnowledge(brainRequest.getContent().split(" ")[0]);
    }
}
