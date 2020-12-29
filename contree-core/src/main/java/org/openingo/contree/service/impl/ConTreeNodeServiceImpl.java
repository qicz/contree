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

package org.openingo.contree.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openingo.contree.base.entity.ConTreeNodeDO;
import org.openingo.contree.mapper.ConTreeNodeMapperX;
import org.openingo.contree.service.IConTreeNodeService;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.jdkits.validate.ValidateKit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 树节点数据 服务实现类
 * </p>
 *
 * @author Qicz
 * @since 2020-12-18
 */
@Service
public class ConTreeNodeServiceImpl extends ServiceImpl<ConTreeNodeMapperX, ConTreeNodeDO> implements IConTreeNodeService {

    /**
     * 获取rootNodeId的子节点
     *
     * @param treeCode   树编码
     * @param rootNodeId 父节点
     * @return 节点list
     */
    @Override
    public List<ConTreeNodeDO> listNodes(String treeCode,
                                         Integer rootNodeId,
                                         boolean recursion) {
        return this.listNodes(treeCode, rootNodeId, null, recursion);
    }

    /**
     * 获取rootNodeId的子节点
     * <note>父节点，为0时即根节点时，不必进行递归处理</note>
     * 1. rootNodeId = 0, recursion = true -> FULL or CASCADE
     * 2. rootNodeId = 0, recursion = false -> SONS or NON-SONS
     * 3. rootNodeId != 0, recursion = true -> FULL or CASCADE
     * 4. rootNodeId != 0, recursion = false -> SONS or NON-SONS
     * @param treeCode 树编码
     * @param rootNodeId 父节点
     * @param nodeName 删除模式
     * @param recursion 是否递归查找
     * @return 节点list
     */
    @Override
    public List<ConTreeNodeDO> listNodes(String treeCode, Integer rootNodeId, String nodeName, boolean recursion) {
        boolean rootNodeZeroId = Integer.valueOf(0).equals(rootNodeId);
        List<ConTreeNodeDO> allNodes = ConTreeNodeDO.dao(ConTreeNodeDO.class)
                .like(ValidateKit.isNotNull(nodeName), ConTreeNodeDO::getNodeName, nodeName)
                .eq(ConTreeNodeDO::getTreeCode, treeCode)
                .eq(!rootNodeZeroId || !recursion, ConTreeNodeDO::getRootNodeId, rootNodeId).doQuery();
        if (rootNodeZeroId || !recursion) {
            return allNodes;
        }
        this.recursiveListNodes(allNodes, allNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList()));
        if (ValidateKit.isNull(allNodes)) {
            allNodes = ListKit.emptyList();
        }
        return allNodes;
    }

    /**
     * 递归获取树节点数据
     * @param allNodes 完整数据
     * @param rootNodeIds 父节点ids
     */
    private void recursiveListNodes(List<ConTreeNodeDO> allNodes, List<Integer> rootNodeIds) {
        if (ValidateKit.isNull(rootNodeIds)) {
            return;
        }
        List<ConTreeNodeDO> partNodes = ConTreeNodeDO.dao(ConTreeNodeDO.class).in(ConTreeNodeDO::getRootNodeId, rootNodeIds).doQuery();
        if (ValidateKit.isNull(partNodes)) {
            return;
        }
        allNodes.addAll(partNodes);
        this.recursiveListNodes(allNodes, partNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList()));
    }
}
