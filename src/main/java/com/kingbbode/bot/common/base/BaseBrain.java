package com.kingbbode.bot.common.base;

import com.kingbbode.bot.common.annotations.Brain;
import com.kingbbode.bot.common.annotations.BrainCell;
import com.kingbbode.bot.common.base.cell.AbstractBrainCell;
import com.kingbbode.bot.common.base.cell.ApiBrainCell;
import com.kingbbode.bot.common.base.cell.CommonBrainCell;
import com.kingbbode.bot.common.base.cell.EmoticonBrainCell;
import com.kingbbode.bot.common.base.factory.BrainFactory;
import com.kingbbode.bot.common.base.knowledge.component.KnowledgeComponent;
import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.util.BrainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by YG-MAC on 2017. 1. 27..
 */
@Brain
public class BaseBrain {

    @Autowired
    private BrainFactory brainFactory;
    
    @Autowired
    private KnowledgeComponent knowledgeComponent;

    @BrainCell(key = "기능", explain = "기능 목록 출력", example = "#기능")
    public <T extends AbstractBrainCell> String explain(BrainRequest brainRequest) {
        if(StringUtils.isEmpty(brainRequest.getContent())){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("울트론 v3.0.0 \n");
            stringBuilder.append("**** 기능 목록 **** \n");
            stringBuilder.append(BrainUtil.explainDetail(brainFactory.findBrainCellByType(CommonBrainCell.class)));
            stringBuilder.append("\n**** API 기능 목록 **** \n");
            stringBuilder.append(BrainUtil.explainDetail(brainFactory.findBrainCellByType(ApiBrainCell.class)));
            stringBuilder.append("\n**** 이모티콘 목록 **** \n");
            stringBuilder.append(BrainUtil.explainSimple(brainFactory.findBrainCellByType(EmoticonBrainCell.class)));
            stringBuilder.append("\n**** 학습 목록 **** \n");
            stringBuilder.append(BrainUtil.explainForKnowledge(knowledgeComponent.getCommands()));
            return stringBuilder.toString();
        }

        return brainFactory.get("#" + brainRequest.getContent().split(" ")[0]).explain();
    }

    @BrainCell(key = "이모티콘", explain = "이모티콘 리스트 출력", example = "#이모티콘")
    public String emoticon(BrainRequest brainRequest){
        Set<Map.Entry<String, EmoticonBrainCell>> emoticonEntrySet = brainFactory.findBrainCellByType(EmoticonBrainCell.class);
        if(emoticonEntrySet.size()>0){
            StringBuilder result = new StringBuilder();
            result
                .append("총 ")
                .append(emoticonEntrySet.size())
                .append("개\n");

            for(Map.Entry<String, EmoticonBrainCell> entry : emoticonEntrySet){
                result
                    .append(entry.getKey())
                    .append("\n");
            }
            return result.toString();
        }else{
            return "등록된 이모티콘이 업습니다";
        }
    }
    
    @BrainCell(key = "팀업고유정보", explain = "팀업 고유 정보 추출", example = "#팀업고유정보")
    public String teamupId(BrainRequest brainRequest){
        return brainRequest.toString();
    }
}
