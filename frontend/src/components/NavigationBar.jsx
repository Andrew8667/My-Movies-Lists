import {
  AppBar,
  Avatar,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  Menu,
  MenuItem,
  Typography,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";

import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

/**
 * The navigation bar contains a drawer for the screens, title, and profile icon
 * Users can navigate to other screens using the drawer and logout by clicking on the profile icon
 * @returns a navigation feature at top of each screen
 */
const NavigationBar = function NavigationBar() {
  const [drawerOpen, setDrawerOpen] = useState(false); //controls visibility of drawer
  const [user, setUser] = useState(null); //the user of the session
  const [menuEl, setMenuEl] = useState(null); //element the menu container is attached to
  const navigate = useNavigate()
  
  useEffect(() => {
    //get the user of the current session
    axios
      .get("http://localhost:8080/user/getUser", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setUser(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <AppBar
      sx={{
        width: "100%",
        height: "75px",
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        backgroundColor: "#000000",
      }}
    >
      <IconButton onClick={() => setDrawerOpen(true)} sx={{ marginLeft: 1 }}>
        <MenuIcon sx={{ color: "#FFFFFF" }}></MenuIcon>
      </IconButton>
      <Typography
        sx={{
          fontFamily: "sans-serif",
          color: "#db0000",
          fontWeight: 600,
          fontSize: 64,
        }}
      >
        MY MOVIES LISTS
      </Typography>
      <IconButton
        onClick={(e) => {
          setMenuEl(e.currentTarget);
        }}
      >
        <Avatar sx={{ marginRight: 1 }}>{user && user.name.charAt(0).toUpperCase()}</Avatar>
      </IconButton>
      <Menu open={menuEl} onClose={() => setMenuEl(null)} anchorEl={menuEl} anchorOrigin={{
        vertical:'bottom',
        horizontal:'left'
      }}>
        <MenuItem onClick={()=>{
            localStorage.removeItem('token')
            navigate('/')
        }}>Logout</MenuItem>
      </Menu>
      <Drawer open={drawerOpen} onClose={() => setDrawerOpen(false)}>
        <List
          sx={{ height: "100%", width: "150px", backgroundColor: "#FFFFFF" }}
        >
          <ListItem>
            <ListItemButton sx={{ justifyContent: "center"}} onClick={()=>navigate('/home')}>
              <Typography sx={{ color: "#000000" }}>Home</Typography>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton sx={{ justifyContent: "center" }} onClick={()=>navigate('/lists')}>
              <Typography sx={{ color: "#000000" }}>My Lists</Typography>
            </ListItemButton>
          </ListItem>
        </List>
      </Drawer>
    </AppBar>
  );
};
export default NavigationBar;
