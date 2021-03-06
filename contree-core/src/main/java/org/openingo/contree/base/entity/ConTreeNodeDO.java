/*
 * MIT License
 *
 * Copyright (c) 2020 OpeningO Co.,Ltd.
 *
 *    https://openingo.org
 *    contactus(at)openingo.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.openingo.contree.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.openingo.mybatisplus.extension.ModelX;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 树节点数据
 * </p>
 *
 * @author Qicz
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_con_tree_node")
public class ConTreeNodeDO extends ModelX<ConTreeNodeDO> {

    private static final long serialVersionUID=1L;
    

    /**
     * 节点id
     */
    @TableId(value = "node_id", type = IdType.AUTO)
    private Integer nodeId;

    /**
     * 父节点id，默认为0
     */
    private Integer rootNodeId;

    /**
     * 树code
     */
    private String treeCode;

    /**
     * 节点图标uri
     */
    private String nodeIcon;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 1可见0不可见
     */
    private Boolean nodeVisible;

    /**
     * 节点顺序
     */
    private Integer nodeOrder;

    /**
     * 节点提示文字
     */
    private String nodeTips;

    /**
     * 扩展信息【json】
     */
    private String nodeExtension;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.nodeId;
    }

}
