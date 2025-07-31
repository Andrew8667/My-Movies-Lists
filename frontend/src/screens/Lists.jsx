import {
  Container,
  Typography,
  AppBar,
  Toolbar,
  IconButton,
  Avatar,
  Box,
  TextField,
  Button,
  Paper,
  Drawer,
  ListItem,
  ListItemText,
  ListItemButton,
  Modal,
  Popover,
} from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";

const Lists = function Lists() {
  const navigate = useNavigate();
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [createModalOpen, setCreateModalOpen] = useState(false);
  const [newListName, setNewListName] = useState("");
  const [description, setDescription] = useState("");
  const [categoriesMoviesRatings, setCategoriesMoviesRatings] = useState([]);
  const [infoAnchorEl, setInfoAnchorEl] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/category/getUserCategories", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => setCategoriesMoviesRatings(response.data))
      .catch((error) => console.log(error));
  }, []);

  const createList = () => {
    axios
      .post(
        "http://localhost:8080/category/create",
        {
          title: newListName,
          description: description,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  };
  return (
    <Container maxWidth={false}>
      <AppBar
        sx={{
          width: "100%",
          top: 0,
          left: 0,
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: "#db0000",
        }}
      >
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
            onClick={() => setDrawerOpen(true)}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            component="div"
            sx={{
              flexGrow: 1,
              fontFamily: "inter",
              fontWeight: 600,
              fontSize: 30,
            }}
          >
            MY MOVIES LISTS
          </Typography>
          <IconButton>
            <Avatar>{name.toUpperCase()}</Avatar>
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        anchor="left"
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        sx={{ width: 30 }}
      >
        <Box
          sx={{
            height: "100%",
            backgroundColor: "#FFFFFF",
            width: 150,
            marginTop: 8,
          }}
        >
          <ListItemButton onClick={() => navigate("/home")}>
            <ListItem>
              <ListItemText primary="Search"></ListItemText>
            </ListItem>
          </ListItemButton>
          <ListItemButton onClick={() => navigate("/lists")}>
            <ListItem>
              <ListItemText primary="Lists"></ListItemText>
            </ListItem>
          </ListItemButton>
        </Box>
      </Drawer>
      <Box sx={{ display: "flex", flexDirection: "column" }}>
        <Button
          variant="contained"
          onClick={() => setCreateModalOpen(true)}
          sx={{ backgroundColor: "#db0000", width: 200 }}
        >
          Create new list
        </Button>
        <Box sx={{ width: "100vw", height: 100, marginTop: 10 }}>
          {Array.isArray(categoriesMoviesRatings) &&
            categoriesMoviesRatings.map((item) => (
              <Box
                key={item.id}
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Typography key={item.id} sx={{ color: "#FFFFFF" }}>
                    {item.title}
                  </Typography>
                  <IconButton onClick={(e) => setInfoAnchorEl(e.currentTarget)}>
                    <InfoIcon color="primary"></InfoIcon>
                  </IconButton>
                  <Popover
                    open={infoAnchorEl ? true : false}
                    anchorEl={infoAnchorEl}
                    onClose={() => setInfoAnchorEl(false)}
                    anchorOrigin={{
                      vertical: "top",
                      horizontal: "right",
                    }}
                  >
                    <Typography>{item.description}</Typography>
                  </Popover>
                </Box>
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "flex-start",
                    width: "100vw",
                    height: 150,
                  }}
                >
                  {Array.isArray(item.movies) &&
                    item.movies.map((movie) => (
                      <Box sx={{width:'100px',height:'100%',marginRight:2,borderRadius:2,overflow:'hidden'}}>
                        <img
                          key={movie.id}
                          src={movie.img}
                          height="100%"
                          width="100%"
                        ></img>
                      </Box>
                    ))}
                </Box>
              </Box>
            ))}
        </Box>
      </Box>
      <Modal
        open={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        sx={{ display: "flex", justifyContent: "center", alignItems: "center" }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <TextField
              label="New list name"
              variant="outlined"
              sx={{ backgroundColor: "#564d4d" }}
              onChange={(e) => {
                setNewListName(e.target.value);
              }}
            ></TextField>
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={createList}
            >
              Create
            </Button>
          </Box>
          <TextField
            label="Description"
            variant="outlined"
            sx={{ backgroundColor: "#564d4d", height: 100, width: 100 }}
            onChange={(e) => {
              setDescription(e.target.value);
            }}
          ></TextField>
        </Box>
      </Modal>
    </Container>
  );
};

export default Lists;
