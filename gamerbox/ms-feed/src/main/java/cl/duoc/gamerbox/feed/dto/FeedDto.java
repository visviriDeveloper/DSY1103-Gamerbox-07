package cl.duoc.gamerbox.feed.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FeedDto {

    @NotNull @Positive
    private Long idUsuario;

    @NotNull @Positive
    private Long feed;

    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long id) { this.idUsuario = id; }
    public Long getFeed() { return feed; } public void setFeed(Long f) { this.feed = f; }
}
