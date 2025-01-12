import React, {useState, useEffect} from "react";
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    Box,
    Typography,
    Button, Container,
} from "@mui/material";
import {cleatAuthToken, getAuthToken, isTokenExpired} from "../utils/auth-utils";
import {getAllUsers} from "../api/mainPageApi";
import {LOGIN_PAGE} from "../constants/path";

interface GitHubUser {
    id: number;
    login: string;
    avatarUrl: string;
}

const MainPage: React.FC = () => {
    const [users, setUsers] = useState<GitHubUser[]>([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [totalUsers, setTotalUsers] = useState(0);

    const fetchUsers = (pageNumber: number, pageSize: number) => {

        getAllUsers(pageNumber, pageSize).then((response) => {
            setUsers(response.data.data);
            setTotalUsers(response.data.totalCount);
        }).catch((error) => {
            console.error("Error fetching users:", error);
        });
    };

    useEffect(() => {
        fetchUsers(page, rowsPerPage);
        const tokenCheckInterval = setInterval(() => {
            const token = getAuthToken();
            if (!token || isTokenExpired(token)) {
                handleLogout();
            }
        }, 60000);

    }, [page, rowsPerPage]);

    const handlePageChange = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleLogout = () => {
        cleatAuthToken();
        window.location.href = LOGIN_PAGE;
    };

    return (
        <Container maxWidth="sm">
            <Box p={4}>
                <Box display="flex" justifyContent="space-between" mb={2}>
                    <Typography variant="h4">GitHub Users</Typography>
                    <Button variant="outlined" color="secondary" onClick={handleLogout}>
                        Log Out
                    </Button>
                </Box>
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell>Username</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {users?.map((user) => (
                                <TableRow key={user.id}>
                                    <TableCell>
                                        <img src={user.avatarUrl} alt={`${user.login}'s avatar`} width="50" style={{ borderRadius: "50%" }}/>
                                    </TableCell>
                                    <TableCell>{user.login}</TableCell>

                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    component="div"
                    count={totalUsers}
                    page={page}
                    onPageChange={handlePageChange}
                    rowsPerPage={rowsPerPage}
                    onRowsPerPageChange={handleRowsPerPageChange}
                />
            </Box>
        </Container>
    );
};

export default MainPage;