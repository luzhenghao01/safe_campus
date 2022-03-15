package com.lzh.electricfence.dto;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import lombok.Data;

/**
 * @author luzhenghao
 */
@Data
public class MessageDto {
  private Integer type;
  private String value;
}
