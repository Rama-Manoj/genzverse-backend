package com.genzverse.dto;

public class ShareResponse
{
    private String shareUrl;

    private long totalShares;

    public ShareResponse(
            String shareUrl,
            long totalShares)
    {
        this.shareUrl = shareUrl;
        this.totalShares = totalShares;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public long getTotalShares() {
        return totalShares;
    }
}