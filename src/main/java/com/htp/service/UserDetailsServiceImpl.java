//package com.htp.service;
//
//import com.htp.repository.jdbc.RoleDao;
//import com.htp.repository.jdbc.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service(value = "userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    @Qualifier("userDaoImpl")
//    private UserDao userDao;
//
//    @Autowired
//    private RoleDao roleDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return null;
//    }
//
//    //    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        try {
////
////            HibernateUser user = userDao.findByLogin(username);
////            List<HibernateRoleDao> roles = roleDao.getRolesByUserId(user.getUserId());
////            if(user.getUserId() == null){
////                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
////            } else {
////                return new org.springframework.security.core.userdetails.HibernateUser(
////                        user.getLogin(),
////                        user.getPassword(),
////                        AuthorityUtils.commaSeparatedStringToAuthorityList(roles.get(0).getRoleName())
////                );
////            }
////        } catch (Exception e) {
////            throw new UsernameNotFoundException("HibernateUser with this login not found");
////        }
////    }
//}