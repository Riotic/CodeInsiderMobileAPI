package com.codeinsider.Interface;

import com.codeinsider.Model.Post;

public interface PostSelectListener {
    void onItemPostClicked(Post post);

    void onItemDeleteClicked(Post post);

    void onItemEditClicked(Post post);
}