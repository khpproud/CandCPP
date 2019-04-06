package com.patrick.rxandroidsample.square;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GitHubServiceApi {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> getCallContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> getObContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @Headers({"Accept: application/vnd.github.v3.full+json"})
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> getCallContributorsWithHeader(
            @Path("owner") String owner,
            @Path("repo") String repo);
}
