package com.nixsolutions.cupboard;

import android.database.Cursor;
import android.test.ProviderTestCase2;

import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.database.CupboardContentProvider;
import com.nixsolutions.cupboard.entities.GitHubOrganization;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;
import com.nixsolutions.cupboard.entities.TestBean;

import java.util.List;
import java.util.Random;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;

public class CupboardProviderTest extends ProviderTestCase2<CupboardContentProvider> {

    private Cupboard cupboard;
    private ProviderCompartment compartment;

    public CupboardProviderTest() {
        super(CupboardContentProvider.class, ContentDescriptor.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        cupboard = CupboardFactory.getInstance();
        compartment = cupboard.withContext(getMockContext());
    }

    public void testAddUser() {

        GitHubUser user = getGitHubUser("chuvak");

        compartment.put(ContentDescriptor.getUri(GitHubUser.class), user);

        GitHubUser savedUser = compartment.get(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class);

        assertEquals(savedUser.getId(), user.getId());
        assertEquals(savedUser.getEventsUrl(), user.getEventsUrl());
        assertEquals(savedUser.getLogin(), user.getLogin());

        assertEquals(1, compartment.query(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class).getCursor().getCount());

    }

    public void testUserWithOrganizationComplexSelect() {
        String login = "chuvak";

        GitHubUser gitHubUser = getGitHubUser(login);
        GitHubOrganization hubOrganization = getHubOrganization(login);

        compartment.put(ContentDescriptor.getUri(GitHubUser.class), gitHubUser);
        compartment.put(ContentDescriptor.getUri(GitHubOrganization.class), hubOrganization);

        Cursor cursor = getMockContentResolver().query(
                ContentDescriptor.getUri(GitHubUserWithOrganizationUnion.class),
                null, null, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(2, cursor.getCount());


        List<GitHubUserWithOrganizationUnion> list = cupboard.withCursor(cursor).list(GitHubUserWithOrganizationUnion.class);

        GitHubUserWithOrganizationUnion userUnion = list.get(0);
        GitHubUserWithOrganizationUnion organizationUnion = list.get(1);

        assertEquals(login, userUnion.getLogin());

        assertEquals(null, organizationUnion.getLogin());
        assertEquals(hubOrganization.getDescription(), organizationUnion.getDescription());
    }

    private GitHubOrganization getHubOrganization(String login) {
        GitHubOrganization organization = new GitHubOrganization();
        organization.setId(22l);
        organization.setLogin(login);
        organization.setMembersUrl("meUrl");
        organization.setAvatarUrl("avatar");
        organization.setDescription("desdsfds");
        return organization;
    }

    public void testQueryById() {
        String login = "testuser";
        Long id = 546789L;

        GitHubUser user = getGitHubUser(login);
        user.setId(id);


        GitHubUser user1 = getGitHubUser("t1");
        GitHubUser user2 = getGitHubUser("t2");

        compartment.put(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class, user, user1, user2);

        List<GitHubUser> list = compartment.query(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class).withSelection("_id =? ", String.valueOf(id)).list();

        assertTrue(list.size() == 1);
        GitHubUser savedUser = list.get(0);
        assertEquals(savedUser.getId(), id);
        assertEquals(savedUser.getLogin(), login);
    }

    public void testEntityConverter() {
        TestBean tB = new TestBean();
        long id = new Random().nextLong();

        tB.set_id(id);
        TestBean.State state = new TestBean.State();
        state.pos = 0;
        state.pos1 = 10;
        state.pos2 = 20;
        tB.setState(state);

        compartment.put(ContentDescriptor.getUri(TestBean.class), TestBean.class, tB);
        TestBean savedBean = compartment.get(ContentDescriptor.getUri(TestBean.class), TestBean.class);

        assertEquals(id, (long) savedBean.get_id());
        assertEquals(state.pos, savedBean.getState().pos);
        assertEquals(state.pos1, savedBean.getState().pos1);
        assertEquals(state.pos2, savedBean.getState().pos2);
    }


    private GitHubUser getGitHubUser(String login) {
        Long testId = 21l;
        String avatarUrl = "13";
        String eventsUrl = "3421";
        String fU = "13";
        String gU = "rewrew";
        String url = "url";

        GitHubUser user = new GitHubUser();
        user.setId(testId);
        user.setAvatarUrl(avatarUrl);
        user.setEventsUrl(eventsUrl);
        user.setFollowersUrl(fU);
        user.setGistsUrl(gU);
        user.setUrl(url);
        user.setLogin(login);
        return user;
    }
}
