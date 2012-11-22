package org.pullrequest.squarelibs.demo.rest;

import java.util.List;

import retrofit.http.GET;

public interface Github {

	@GET("orgs/square/repos")
	List<Repo> getSquareRepos();

	@GET("orgs/square")
	Org getSquare();
}
