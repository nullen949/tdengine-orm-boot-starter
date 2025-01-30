package com.nullen.tdengineorm.entity;

import com.nullen.tdengineorm.annotation.PrimaryTs;
import lombok.Data;

/**
 * @author Nullen
 */
@Data
public class BaseTdEntity {

    @PrimaryTs
    private Long ts;
}
