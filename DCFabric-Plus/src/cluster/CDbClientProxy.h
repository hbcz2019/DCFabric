/*
 * BNC SDN Controller GPL Source Code
 * Copyright (C) 2016, BNC <DCFabric-admin@bnc.org.cn>
 *
 * This file is part of the BNC SDN Controller. BNC SDN
 * Controller is a free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, , see <http://www.gnu.org/licenses/>.
 */

/******************************************************************************
*                                                                             *
*   File Name   : CDbClientProxy.h                                            *
*   Author      : bnc bojiang                                                 *
*   Create Date : 2017-10-30                                                  *
*   Version     : 1.0                                                         *
*   Function    :                                                             *
*                                                                             *
******************************************************************************/
#ifndef __CDBCLIENTPROXY_H
#define __CDBCLIENTPROXY_H

#include "bnc-type.h"
#include "CDbClient.h"

/*
 * DB客户端代理类
 */
class CDbClientProxy : public CDbClient
{
public:
    /*
     * 默认构造函数
     */
    CDbClientProxy();

    /*
     * 默认析构函数
     */
    virtual ~CDbClientProxy();

    /*
     * 初始化
     *
     * @return: 成功 or 失败
     */
    INT4 init();

    /*
     * 根据nodeType持久化数据
     *
     * @return: 成功 or 失败
     */
    INT4 persistData(INT4 nodeType, const INT1* data, UINT4 len);

    /*
     * 根据key持久化数据
     *
     * @return: 成功 or 失败
     */
    INT4 persistData(const INT1* key, const INT1* data, UINT4 len);

    /*
     * 根据key持久化相应的数据
     *
     * @return: 成功 or 失败
     */
    INT4 persistValue(const INT1* key, const INT1* val);
    
    /*
     * 根据nodeType请求相应的数据
     *
     * @return: 成功 or 失败
     */
    INT4 queryData(INT4 nodeType, INT1* data, UINT4& len);

    /*
     * 根据key请求相应的数据
     *
     * @return: 成功 or 失败
     */
    INT4 queryData(const INT1* key, INT1* data, UINT4& len);

    /*
     * 根据key请求相应的数据
     *
     * @return: 成功 or 失败
     */
    INT4 queryValue(const INT1* key, INT1* val);
    
private:
    CDbClient* m_client;
};


#endif
