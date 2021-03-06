/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cdancy.etcd.rest.features;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.binders.BindToJsonPayload;

import com.cdancy.etcd.rest.domain.members.CreateMember;
import com.cdancy.etcd.rest.domain.members.Member;
import com.cdancy.etcd.rest.fallbacks.EtcdFallbacks.MemberOnIllegalRequest;
import com.cdancy.etcd.rest.filters.EtcdAuthentication;

@RequestFilters(EtcdAuthentication.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/{jclouds.api-version}/members")
public interface MembersApi {

    @Named("members:list")
    @SelectJson("members")
    @GET
    List<Member> list();

    @Named("members:add")
    @Fallback(MemberOnIllegalRequest.class)
    @POST
    Member add(@BinderParam(BindToJsonPayload.class) CreateMember memberToCreate);

    @Named("members:delete")
    @Path("/{id}")
    @Fallback(FalseOnNotFoundOr404.class)
    @DELETE
    boolean delete(@PathParam("id") String memberID);
}
