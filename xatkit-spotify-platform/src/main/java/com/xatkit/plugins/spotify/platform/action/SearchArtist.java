package com.xatkit.plugins.spotify.platform.action;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.spotify.platform.SpotifyPlatform;
import com.xatkit.core.XatkitException;
import lombok.NonNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;


/**
 * A {@link RuntimeAction} that retrieves a GIF from a given search string.
 * <p>
 * This class relies on the {@link SpotifyPlatform} to access the Spotify API using the token stored in the
 * {@link org.apache.commons.configuration2.Configuration}.
 */
public class SearchArtist extends RuntimeAction < SpotifyPlatform > {
    /**
     * The {@link String} used to search GIFs.
     */
    private String searchString;


    /**
     * Constructs a new {@link CreatePlaylist} with the provided {@code platform}, {@code context}, and {@code searchString}.
     * <p>
     * This constructor requires a valid Spotify API token in order to build the REST query used to retrieve GIF urls.
     *
     * @param platform     the {@link SpotifyPlatform} containing this action
     * @param context      the {@link StateContext} associated to this action
     * @param searchString the {@link String} used to search GIFs
     * @throws NullPointerException if the provided {@code runtimePlatform}, {@code session}, or {@code searchString}
     *                              is {@code null}
     * @see SpotifyPlatform#getSpotifyToken()
     */
    public SearchArtist(@NonNull SpotifyPlatform platform, @NonNull StateContext context, @NonNull String searchString) {
        super(platform, context);
        this.searchString = searchString;
        this.context = context;
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @Override
    protected Object compute() throws Exception {
        try {    
            String token = this.runtimePlatform.getSpotifyToken();
            String endpoint = "https://api.spotify.com/v1/search?q=" + encodeValue(this.searchString) + "&type=artist&limit=1";
            
            JsonNode jsonNode = Unirest.get(endpoint)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .asJson()
                .getBody();

            String link = jsonNode.getObject()
                .getJSONObject("artists")
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("external_urls")
                .getString("spotify");

            String name = jsonNode.getObject()
                .getJSONObject("artists")
                .getJSONArray("items")
                .getJSONObject(0)
                .getString("name");

            return link + ";" + name;
        } catch (Exception e) {
            throw new XatkitException("Cannot search artist, see attached exception", e);
        }
    }
}