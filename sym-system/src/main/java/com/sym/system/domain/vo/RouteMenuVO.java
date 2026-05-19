package com.sym.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteMenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String path;

    private String component;

    private String redirect;

    private Meta meta;

    private List<RouteMenuVO> children;

    @Data
    public static class Meta implements Serializable {
        private static final long serialVersionUID = 1L;

        private String title;

        private String icon;

        private Boolean visible;

        private Boolean keepAlive;

        private Boolean affix;

        public Meta() {
        }

        public Meta(String title, String icon, Boolean visible, Boolean keepAlive) {
            this.title = title;
            this.icon = icon;
            this.visible = visible;
            this.keepAlive = keepAlive;
        }
    }
}
