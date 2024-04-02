package com.sxhta.cloud.gateway.filter;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BlackListUrlFilterConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private List<String> blacklistUrl;

    private final List<Pattern> blacklistUrlPattern = new ArrayList<>();

    public Boolean matchBlacklist(String url) {
        return !blacklistUrlPattern.isEmpty() && blacklistUrlPattern.stream().anyMatch(p -> p.matcher(url).find());
    }

    public void setBlacklistUrl(List<String> blacklistUrl) {
        this.blacklistUrl = blacklistUrl;
        this.blacklistUrlPattern.clear();
        this.blacklistUrl.forEach(url -> this.blacklistUrlPattern.add(Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE)));
    }
}
