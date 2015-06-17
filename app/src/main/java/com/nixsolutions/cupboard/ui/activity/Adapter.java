package com.nixsolutions.cupboard.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Adapter extends RecyclerView.Adapter<Adapter.BaseHolder> {

    private Context context;

    private static final int USER_ITEM_TYPE = 0;
    private static final int ORGANIZATION_ITEM_TYPE = 1;

    public static final SparseArray<Integer> viewArray = new SparseArray<>();

    static {
        viewArray.put(USER_ITEM_TYPE, R.layout.user_item);
        viewArray.put(ORGANIZATION_ITEM_TYPE, R.layout.organization_item);
    }

    private List<GitHubUserWithOrganizationUnion> list;

    public Adapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = null;

        switch (viewType) {
            case USER_ITEM_TYPE: {
                View view = LayoutInflater.from(context).inflate(viewArray.get(USER_ITEM_TYPE), parent, false);
                holder = new UserHolder(view);

            }
            break;
            case ORGANIZATION_ITEM_TYPE: {
                View view = LayoutInflater.from(context).inflate(viewArray.get(ORGANIZATION_ITEM_TYPE), parent, false);
                holder = new OrganizationHolder(view);
            }
            break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        String image;
        String text = "";

        GitHubUserWithOrganizationUnion userInfo = list.get(position);


        if (holder instanceof UserHolder) {
            text = userInfo.getLogin();
            image = userInfo.getAvatarUrl();
        } else {
            image = userInfo.getOrganizationAvatarUrl();
            text = userInfo.getDescription();
        }

        Glide.with(context).load(image).into(holder.image);
        holder.text.setText(text);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getLogin() != null
                ? USER_ITEM_TYPE
                : ORGANIZATION_ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public void setList(List<GitHubUserWithOrganizationUnion> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        public ImageView image;
        @InjectView(R.id.text)
        public TextView text;

        public BaseHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public static class UserHolder extends BaseHolder {
        public UserHolder(View view) {
            super(view);
        }
    }

    public static class OrganizationHolder extends BaseHolder {
        public OrganizationHolder(View view) {
            super(view);
        }
    }
}
