package br.com.neotech.framework.permissoes;

import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import lombok.extern.log4j.Log4j;

@Log4j
public class ActiveDirectory {

    private ActiveDirectory() {
    }

    /**
     * Used to authenticate a user given a username/password and domain name.
     * Provides an option to identify a specific a Active Directory server.
     */
    public static LdapContext getConnection(String username, String password,
        String domainName, String serverName, Integer port)
        throws NamingException {

        String dn = domainName;
        if (dn == null) {
            try {
                String fqdn = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                if (fqdn.split("\\.").length > 1) {
                    dn = fqdn.substring(fqdn.indexOf(".") + 1);
                }
            } catch (java.net.UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }

        String pw = password;
        if (pw != null) {
            pw = pw.trim();
            if (pw.length() == 0) {
                pw = null;
            }
        }

        Map<String, String> props = new Hashtable<String, String>();
        String principalName = username + "@" + dn;
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        if (pw != null) {
            props.put(Context.SECURITY_CREDENTIALS, pw);
        }

        String p = Integer.toString(port != null ? port : 389);
        String ldapURL = "ldap://" + ((serverName == null) ? dn : serverName + ":" + p + "/");
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, ldapURL);

        return new InitialLdapContext((Hashtable<?, ?>) props, null);

    }

    public static List<String[]> getUsers(LdapContext context,
        String searchBase, String searchFilter)
        throws NamingException {

        String[] userAttributes = { "cn", "givenName", "mail", "sAMAccountName" };

        List<String[]> users = new ArrayList<String[]>();

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SUBTREE_SCOPE);
        controls.setReturningAttributes(userAttributes);

        NamingEnumeration<SearchResult> answer = context.search(searchBase, searchFilter, controls);

        while (answer.hasMore()) {
            Attributes attr = answer.next().getAttributes();

            int i = 0;
            String[] r = new String[4];
            for (String ua : userAttributes) {
                if (attr.get(ua) != null) {
                    r[i] = attr.get(ua).toString().replaceAll(ua + "\\:.(.+)", "$1");
                }
                i++;
            }
            users.add(r);
        }

        return users;
    }

}